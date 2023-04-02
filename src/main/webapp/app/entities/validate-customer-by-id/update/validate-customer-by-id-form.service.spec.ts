import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../validate-customer-by-id.test-samples';

import { ValidateCustomerByIdFormService } from './validate-customer-by-id-form.service';

describe('ValidateCustomerById Form Service', () => {
  let service: ValidateCustomerByIdFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ValidateCustomerByIdFormService);
  });

  describe('Service methods', () => {
    describe('createValidateCustomerByIdFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createValidateCustomerByIdFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customnerId: expect.any(Object),
            billCode: expect.any(Object),
          })
        );
      });

      it('passing IValidateCustomerById should create a new form with FormGroup', () => {
        const formGroup = service.createValidateCustomerByIdFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            customnerId: expect.any(Object),
            billCode: expect.any(Object),
          })
        );
      });
    });

    describe('getValidateCustomerById', () => {
      it('should return NewValidateCustomerById for default ValidateCustomerById initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createValidateCustomerByIdFormGroup(sampleWithNewData);

        const validateCustomerById = service.getValidateCustomerById(formGroup) as any;

        expect(validateCustomerById).toMatchObject(sampleWithNewData);
      });

      it('should return NewValidateCustomerById for empty ValidateCustomerById initial value', () => {
        const formGroup = service.createValidateCustomerByIdFormGroup();

        const validateCustomerById = service.getValidateCustomerById(formGroup) as any;

        expect(validateCustomerById).toMatchObject({});
      });

      it('should return IValidateCustomerById', () => {
        const formGroup = service.createValidateCustomerByIdFormGroup(sampleWithRequiredData);

        const validateCustomerById = service.getValidateCustomerById(formGroup) as any;

        expect(validateCustomerById).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IValidateCustomerById should not enable id FormControl', () => {
        const formGroup = service.createValidateCustomerByIdFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewValidateCustomerById should disable id FormControl', () => {
        const formGroup = service.createValidateCustomerByIdFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
