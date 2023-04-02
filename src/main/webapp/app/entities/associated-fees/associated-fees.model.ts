import dayjs from 'dayjs/esm';
import { IPayment } from 'app/entities/payment/payment.model';

export interface IAssociatedFees {
  id: number;
  recordUniqueIdentifier?: string | null;
  feeId?: number | null;
  feeCode?: string | null;
  feeDescription?: string | null;
  status?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  isDeleted?: dayjs.Dayjs | null;
  payment?: Pick<IPayment, 'id'> | null;
}

export type NewAssociatedFees = Omit<IAssociatedFees, 'id'> & { id: null };
