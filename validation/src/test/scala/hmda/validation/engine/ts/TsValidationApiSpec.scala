package hmda.validation.engine.ts

import hmda.validation.api.ts.TsValidationApi

import scala.concurrent.{ ExecutionContext, Future }

trait TsValidationApiSpec extends TsValidationApi {

  /*
   The following methods simulate API calls to get values from remote resources
  */

  /*
    Gets latest timestamp from database (see S013)
   */
  override def findTimestamp(implicit ec: ExecutionContext): Future[Long] = Future(201301111330L)

  /*
    Returns year to be processed (see S100)
   */
  override def findYearProcessed(implicit ec: ExecutionContext): Future[Int] = Future(2017)

}
