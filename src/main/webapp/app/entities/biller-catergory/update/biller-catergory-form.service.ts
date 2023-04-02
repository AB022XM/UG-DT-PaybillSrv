import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IBillerCatergory, NewBillerCatergory } from '../biller-catergory.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBillerCatergory for edit and NewBillerCatergoryFormGroupInput for create.
 */
type BillerCatergoryFormGroupInput = IBillerCatergory | PartialWithRequiredKeyOf<NewBillerCatergory>;

type BillerCatergoryFormDefaults = Pick<NewBillerCatergory, 'id' | 'isDeleted'>;

type BillerCatergoryFormGroupContent = {
  id: FormControl<IBillerCatergory['id'] | NewBillerCatergory['id']>;
  recordUniqueIdentifier: FormControl<IBillerCatergory['recordUniqueIdentifier']>;
  categoryId: FormControl<IBillerCatergory['categoryId']>;
  categoryCode: FormControl<IBillerCatergory['categoryCode']>;
  categoryName: FormControl<IBillerCatergory['categoryName']>;
  categoryDescription: FormControl<IBillerCatergory['categoryDescription']>;
  status: FormControl<IBillerCatergory['status']>;
  freeField1: FormControl<IBillerCatergory['freeField1']>;
  freeField2: FormControl<IBillerCatergory['freeField2']>;
  freeField3: FormControl<IBillerCatergory['freeField3']>;
  isDeleted: FormControl<IBillerCatergory['isDeleted']>;
};

export type BillerCatergoryFormGroup = FormGroup<BillerCatergoryFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BillerCatergoryFormService {
  createBillerCatergoryFormGroup(billerCatergory: BillerCatergoryFormGroupInput = { id: null }): BillerCatergoryFormGroup {
    const billerCatergoryRawValue = {
      ...this.getFormDefaults(),
      ...billerCatergory,
    };
    return new FormGroup<BillerCatergoryFormGroupContent>({
      id: new FormControl(
        { value: billerCatergoryRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(billerCatergoryRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      categoryId: new FormControl(billerCatergoryRawValue.categoryId),
      categoryCode: new FormControl(billerCatergoryRawValue.categoryCode, {
        validators: [Validators.required],
      }),
      categoryName: new FormControl(billerCatergoryRawValue.categoryName, {
        validators: [Validators.required],
      }),
      categoryDescription: new FormControl(billerCatergoryRawValue.categoryDescription, {
        validators: [Validators.required],
      }),
      status: new FormControl(billerCatergoryRawValue.status, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(billerCatergoryRawValue.freeField1),
      freeField2: new FormControl(billerCatergoryRawValue.freeField2),
      freeField3: new FormControl(billerCatergoryRawValue.freeField3),
      isDeleted: new FormControl(billerCatergoryRawValue.isDeleted),
    });
  }

  getBillerCatergory(form: BillerCatergoryFormGroup): IBillerCatergory | NewBillerCatergory {
    return form.getRawValue() as IBillerCatergory | NewBillerCatergory;
  }

  resetForm(form: BillerCatergoryFormGroup, billerCatergory: BillerCatergoryFormGroupInput): void {
    const billerCatergoryRawValue = { ...this.getFormDefaults(), ...billerCatergory };
    form.reset(
      {
        ...billerCatergoryRawValue,
        id: { value: billerCatergoryRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BillerCatergoryFormDefaults {
    return {
      id: null,
      isDeleted: false,
    };
  }
}
