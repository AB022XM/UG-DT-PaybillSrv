import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../associated-fees.test-samples';

import { AssociatedFeesFormService } from './associated-fees-form.service';

describe('AssociatedFees Form Service', () => {
  let service: AssociatedFeesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssociatedFeesFormService);
  });

  describe('Service methods', () => {
    describe('createAssociatedFeesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssociatedFeesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            feeId: expect.any(Object),
            feeCode: expect.any(Object),
            feeDescription: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            isDeleted: expect.any(Object),
            payment: expect.any(Object),
          })
        );
      });

      it('passing IAssociatedFees should create a new form with FormGroup', () => {
        const formGroup = service.createAssociatedFeesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            recordUniqueIdentifier: expect.any(Object),
            feeId: expect.any(Object),
            feeCode: expect.any(Object),
            feeDescription: expect.any(Object),
            status: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            createdAt: expect.any(Object),
            updatedAt: expect.any(Object),
            isDeleted: expect.any(Object),
            payment: expect.any(Object),
          })
        );
      });
    });

    describe('getAssociatedFees', () => {
      it('should return NewAssociatedFees for default AssociatedFees initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAssociatedFeesFormGroup(sampleWithNewData);

        const associatedFees = service.getAssociatedFees(formGroup) as any;

        expect(associatedFees).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssociatedFees for empty AssociatedFees initial value', () => {
        const formGroup = service.createAssociatedFeesFormGroup();

        const associatedFees = service.getAssociatedFees(formGroup) as any;

        expect(associatedFees).toMatchObject({});
      });

      it('should return IAssociatedFees', () => {
        const formGroup = service.createAssociatedFeesFormGroup(sampleWithRequiredData);

        const associatedFees = service.getAssociatedFees(formGroup) as any;

        expect(associatedFees).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssociatedFees should not enable id FormControl', () => {
        const formGroup = service.createAssociatedFeesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssociatedFees should disable id FormControl', () => {
        const formGroup = service.createAssociatedFeesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
