import { RecordStatus } from 'app/entities/enumerations/record-status.model';

export interface IBillerCatergory {
  id: number;
  recordUniqueIdentifier?: string | null;
  categoryId?: number | null;
  categoryCode?: string | null;
  categoryName?: string | null;
  categoryDescription?: string | null;
  status?: RecordStatus | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  isDeleted?: boolean | null;
}

export type NewBillerCatergory = Omit<IBillerCatergory, 'id'> & { id: null };
