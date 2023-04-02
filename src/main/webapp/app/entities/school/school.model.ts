import dayjs from 'dayjs/esm';
import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface ISchool {
  id: number;
  recordUniqueIdentifier?: string | null;
  schoolId?: number | null;
  schoolCode?: string | null;
  schoolPhoneNumber?: string | null;
  schoolAlternativePhoneNumber?: string | null;
  schoolemailAddess?: string | null;
  schoolName?: string | null;
  status?: RecordStatus | null;
  freeField1?: string | null;
  registrationDate?: dayjs.Dayjs | null;
  approvedDate?: dayjs.Dayjs | null;
  freeField2?: string | null;
  freeField3?: string | null;
  isDeleted?: dayjs.Dayjs | null;
}

export type NewSchool = Omit<ISchool, 'id'> & { id: null };
