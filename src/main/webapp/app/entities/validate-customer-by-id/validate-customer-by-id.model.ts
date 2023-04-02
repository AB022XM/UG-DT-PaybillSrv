export interface IValidateCustomerById {
  id: number;
  customnerId?: number | null;
  billCode?: number | null;
}

export type NewValidateCustomerById = Omit<IValidateCustomerById, 'id'> & { id: null };
