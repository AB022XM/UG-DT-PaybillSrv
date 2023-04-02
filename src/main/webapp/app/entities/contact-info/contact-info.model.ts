import { IStudent } from 'app/entities/student/student.model';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface IContactInfo {
  id: number;
  recordUniqueIdentifier?: string | null;
  contactId?: string | null;
  phoneNumber?: string | null;
  emailAddress?: string | null;
  parentsPhoneNumber?: string | null;
  status?: RecordStatus | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  student?: Pick<IStudent, 'id'> | null;
}

export type NewContactInfo = Omit<IContactInfo, 'id'> & { id: null };
