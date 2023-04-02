import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ISchool, NewSchool } from '../school.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISchool for edit and NewSchoolFormGroupInput for create.
 */
type SchoolFormGroupInput = ISchool | PartialWithRequiredKeyOf<NewSchool>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ISchool | NewSchool> = Omit<T, 'registrationDate' | 'approvedDate'> & {
  registrationDate?: string | null;
  approvedDate?: string | null;
};

type SchoolFormRawValue = FormValueOf<ISchool>;

type NewSchoolFormRawValue = FormValueOf<NewSchool>;

type SchoolFormDefaults = Pick<NewSchool, 'id' | 'registrationDate' | 'approvedDate'>;

type SchoolFormGroupContent = {
  id: FormControl<SchoolFormRawValue['id'] | NewSchool['id']>;
  recordUniqueIdentifier: FormControl<SchoolFormRawValue['recordUniqueIdentifier']>;
  schoolId: FormControl<SchoolFormRawValue['schoolId']>;
  schoolCode: FormControl<SchoolFormRawValue['schoolCode']>;
  schoolPhoneNumber: FormControl<SchoolFormRawValue['schoolPhoneNumber']>;
  schoolAlternativePhoneNumber: FormControl<SchoolFormRawValue['schoolAlternativePhoneNumber']>;
  schoolemailAddess: FormControl<SchoolFormRawValue['schoolemailAddess']>;
  schoolName: FormControl<SchoolFormRawValue['schoolName']>;
  status: FormControl<SchoolFormRawValue['status']>;
  freeField1: FormControl<SchoolFormRawValue['freeField1']>;
  registrationDate: FormControl<SchoolFormRawValue['registrationDate']>;
  approvedDate: FormControl<SchoolFormRawValue['approvedDate']>;
  freeField2: FormControl<SchoolFormRawValue['freeField2']>;
  freeField3: FormControl<SchoolFormRawValue['freeField3']>;
  isDeleted: FormControl<SchoolFormRawValue['isDeleted']>;
};

export type SchoolFormGroup = FormGroup<SchoolFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SchoolFormService {
  createSchoolFormGroup(school: SchoolFormGroupInput = { id: null }): SchoolFormGroup {
    const schoolRawValue = this.convertSchoolToSchoolRawValue({
      ...this.getFormDefaults(),
      ...school,
    });
    return new FormGroup<SchoolFormGroupContent>({
      id: new FormControl(
        { value: schoolRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      recordUniqueIdentifier: new FormControl(schoolRawValue.recordUniqueIdentifier, {
        validators: [Validators.required],
      }),
      schoolId: new FormControl(schoolRawValue.schoolId, {
        validators: [Validators.required],
      }),
      schoolCode: new FormControl(schoolRawValue.schoolCode, {
        validators: [Validators.required],
      }),
      schoolPhoneNumber: new FormControl(schoolRawValue.schoolPhoneNumber),
      schoolAlternativePhoneNumber: new FormControl(schoolRawValue.schoolAlternativePhoneNumber),
      schoolemailAddess: new FormControl(schoolRawValue.schoolemailAddess),
      schoolName: new FormControl(schoolRawValue.schoolName, {
        validators: [Validators.required],
      }),
      status: new FormControl(schoolRawValue.status, {
        validators: [Validators.required],
      }),
      freeField1: new FormControl(schoolRawValue.freeField1),
      registrationDate: new FormControl(schoolRawValue.registrationDate),
      approvedDate: new FormControl(schoolRawValue.approvedDate),
      freeField2: new FormControl(schoolRawValue.freeField2),
      freeField3: new FormControl(schoolRawValue.freeField3),
      isDeleted: new FormControl(schoolRawValue.isDeleted),
    });
  }

  getSchool(form: SchoolFormGroup): ISchool | NewSchool {
    return this.convertSchoolRawValueToSchool(form.getRawValue() as SchoolFormRawValue | NewSchoolFormRawValue);
  }

  resetForm(form: SchoolFormGroup, school: SchoolFormGroupInput): void {
    const schoolRawValue = this.convertSchoolToSchoolRawValue({ ...this.getFormDefaults(), ...school });
    form.reset(
      {
        ...schoolRawValue,
        id: { value: schoolRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): SchoolFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      registrationDate: currentTime,
      approvedDate: currentTime,
    };
  }

  private convertSchoolRawValueToSchool(rawSchool: SchoolFormRawValue | NewSchoolFormRawValue): ISchool | NewSchool {
    return {
      ...rawSchool,
      registrationDate: dayjs(rawSchool.registrationDate, DATE_TIME_FORMAT),
      approvedDate: dayjs(rawSchool.approvedDate, DATE_TIME_FORMAT),
    };
  }

  private convertSchoolToSchoolRawValue(
    school: ISchool | (Partial<NewSchool> & SchoolFormDefaults)
  ): SchoolFormRawValue | PartialWithRequiredKeyOf<NewSchoolFormRawValue> {
    return {
      ...school,
      registrationDate: school.registrationDate ? school.registrationDate.format(DATE_TIME_FORMAT) : undefined,
      approvedDate: school.approvedDate ? school.approvedDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
