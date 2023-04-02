import { PaymentChannel } from 'app/entities/enumerations/payment-channel.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface IPaymentChannel {
  id: number;
  recordUniqueIdentifier?: string | null;
  channelId?: number | null;
  channelCode?: number | null;
  channelName?: PaymentChannel | null;
  status?: RecordStatus | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  isDeleted?: RecordStatus | null;
}

export type NewPaymentChannel = Omit<IPaymentChannel, 'id'> & { id: null };
