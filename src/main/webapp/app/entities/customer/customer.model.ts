import dayjs from 'dayjs/esm';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface ICustomer {
  id: number;
  recordUniqueIdentifier?: string | null;
  contactId?: number | null;
  addressId?: number | null;
  customerId?: string | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  status?: RecordStatus | null;
  customerAddressId?: string | null;
  customerContactId?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
}

export type NewCustomer = Omit<ICustomer, 'id'> & { id: null };
