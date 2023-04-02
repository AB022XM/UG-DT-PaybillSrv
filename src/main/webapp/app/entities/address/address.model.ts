import dayjs from 'dayjs/esm';

export interface IAddress {
  id: number;
  recordUniqueIdentifier?: string | null;
  addressId?: number | null;
  addresssName?: string | null;
  addressDescription?: string | null;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  isDeleted?: dayjs.Dayjs | null;
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
