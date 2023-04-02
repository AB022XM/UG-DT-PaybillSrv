import dayjs from 'dayjs/esm';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface IStudent {
  id: number;
  recordUniqueIdentifier?: string | null;
  studentId?: number | null;
  studentCode?: number | null;
  studentClassId?: number | null;
  contactId?: number | null;
  addressId?: number | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  paymentCode?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  outStandingAmount?: string | null;
  status?: RecordStatus | null;
  billerContact?: string | null;
  billerAddress?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  isDeleted?: dayjs.Dayjs | null;
}

export type NewStudent = Omit<IStudent, 'id'> & { id: null };
