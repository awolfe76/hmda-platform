package hmda.validation.rules.lar.quality

import hmda.model.fi.lar.LoanApplicationRegister
import hmda.validation.rules.EditCheck
import hmda.validation.rules.lar.{ BadValueUtils, LarEditCheckSpec }
import org.scalacheck.Gen

import com.typesafe.config.ConfigFactory

class Q005Spec extends LarEditCheckSpec with BadValueUtils {
  val config = ConfigFactory.load()
  val loanAmount = config.getInt("hmda.validation.quality.Q005.loan.amount")

  property("All lars with loan amounts less than $1203 must pass") {
    forAll(larGen, Gen.choose(0, loanAmount)) { (lar, x) =>
      val newLoan = lar.loan.copy(amount = x)
      val newLar = lar.copy(loan = newLoan)
      newLar.mustPass
    }
  }

  val irrelevantPurchaser: Gen[Int] = intOutsideRange(1, 4)

  property("Whenever purchaser type is irrelevant, lar must pass") {
    forAll(larGen, irrelevantPurchaser) { (lar, x) =>
      val newLar = lar.copy(purchaserType = x)
      newLar.mustPass
    }
  }

  val irrelevantProperty: Gen[Int] = intOutsideRange(1, 2)

  property("Whenever property type is irrelevant, lar must pass") {
    forAll(larGen, irrelevantProperty) { (lar, x) =>
      val newLoan = lar.loan.copy(propertyType = x)
      val newLar = lar.copy(loan = newLoan)
      newLar.mustPass
    }
  }

  property("A lar with relevant property and purchaser types with a loan amount > 1203 must fail") {
    forAll(larGen, Gen.choose(1, 4), Gen.oneOf(1, 2), Gen.choose(loanAmount + 1, Int.MaxValue)) { (lar, x, y, amount) =>
      val newLoan = lar.loan.copy(propertyType = y, amount = amount)
      val newLar = lar.copy(purchaserType = x, loan = newLoan)
      newLar.mustFail
    }
  }

  override def check: EditCheck[LoanApplicationRegister] = Q005
}
