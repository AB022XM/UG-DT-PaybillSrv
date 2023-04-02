import dayjs from 'dayjs/esm';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface IBiller {
  id: number;
  recordUniqueIdentifier?: string | null;
  billerId?: number | null;
  billerCode?: number | null;
  categoryId?: number | null;
  contactId?: number | null;
  addressId?: number | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  outStandingAmount?: string | null;
  status?: RecordStatus | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  isDeleted?: dayjs.Dayjs | null;
}

export type NewBiller = Omit<IBiller, 'id'> & { id: null };
