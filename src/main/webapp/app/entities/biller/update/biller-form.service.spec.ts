import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../biller.test-samples';

import { BillerFormService } from './biller-form.service';

describe('Biller Form Service', () => {
  let service: BillerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BillerFormService);
  });

  describe('Service methods', () => {
    describe('createBillerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createBillerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            billerId: expect.any(Object),
            billerCode: expect.any(Object),
            categoryId: expect.any(Object),
            contactId: expect.any(Object),
            addressId: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            dateOfBirth: expect.any(Object),
            outStandingAmount: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });

      it('passing IBiller should create a new form with FormGroup', () => {
        const formGroup = service.createBillerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            billerId: expect.any(Object),
            billerCode: expect.any(Object),
            categoryId: expect.any(Object),
            contactId: expect.any(Object),
            addressId: expect.any(Object),
            firstName: expect.any(Object),
            middleName: expect.any(Object),
            lastName: expect.any(Object),
            dateOfBirth: expect.any(Object),
            outStandingAmount: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            isDeleted: expect.any(Object),
          })
        );
      });
    });

    describe('getBiller', () => {
      it('should return NewBiller for default Biller initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createBillerFormGroup(sampleWithNewData);

        const biller = service.getBiller(formGroup) as any;

        expect(biller).toMatchObject(sampleWithNewData);
      });

      it('should return NewBiller for empty Biller initial value', () => {
        const formGroup = service.createBillerFormGroup();

        const biller = service.getBiller(formGroup) as any;

        expect(biller).toMatchObject({});
      });

      it('should return IBiller', () => {
        const formGroup = service.createBillerFormGroup(sampleWithRequiredData);

        const biller = service.getBiller(formGroup) as any;

        expect(biller).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IBiller should not enable id FormControl', () => {
        const formGroup = service.createBillerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewBiller should disable id FormControl', () => {
        const formGroup = service.createBillerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
