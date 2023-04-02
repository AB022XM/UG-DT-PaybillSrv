import { IBiller } from 'app/entities/biller/biller.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

export interface IPayment {
  id: number;
  recordUniqueIdentifier?: string | null;
  paymentId?: number | null;
  paymentCode?: number | null;
  customerId?: string | null;
  feeAmount?: number | null;
  isAmountFixed?: RecordStatus | null;
  feeDescription?: string | null;
  fixedAmount?: string | null;
  paymentName?: string | null;
  outstandingAmount?: number | null;
  paymentChannel?: PaymentChannel | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  biller?: Pick<IBiller, 'id'> | null;
}

export type NewPayment = Omit<IPayment, 'id'> & { id: null };
