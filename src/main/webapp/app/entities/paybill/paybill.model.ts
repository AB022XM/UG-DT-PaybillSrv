import dayjs from 'dayjs/esm';
import { IBiller } from 'app/entities/biller/biller.model';
import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';

export interface IPaybill {
  id: number;
  recordUniqueIdentifier?: string | null;
  processTimestamp?: dayjs.Dayjs | null;
  feeAmount?: number | null;
  feeDescription?: string | null;
  feeDueFromDate?: dayjs.Dayjs | null;
  feeDueToDate?: dayjs.Dayjs | null;
  feeId?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  middleName?: string | null;
  outstandingAmount?: number | null;
  paymentCode?: string | null;
  schoolCode?: string | null;
  schoolName?: string | null;
  studentClass?: string | null;
  paymentChannel?: PaymentChannel | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  biller?: Pick<IBiller, 'id'> | null;
}

export type NewPaybill = Omit<IPaybill, 'id'> & { id: null };
