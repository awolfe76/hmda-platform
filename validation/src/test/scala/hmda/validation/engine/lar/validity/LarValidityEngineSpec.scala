package hmda.validation.engine.lar.validity

import java.io.File

import hmda.parser.fi.lar.LarCsvParser
import hmda.validation.context.ValidationContext
import org.scalatest.{ MustMatchers, WordSpec }

import scala.io.Source

class LarValidityEngineSpec extends WordSpec with MustMatchers with LarValidityEngine {

  "LAR Validity engine" must {
    "pass validation on valid sample file" in {
      val lines = Source.fromFile(new File("parser/src/test/resources/txt/FirstTestBankData_clean_407_2017.txt")).getLines()
      val lars = lines.drop(1).map(line => LarCsvParser(line)).collect {
        case Right(lar) => lar
      }

      lars.foreach { lar =>
        checkValidity(lar, ValidationContext(None)).isSuccess mustBe true
      }
    }
  }

}
