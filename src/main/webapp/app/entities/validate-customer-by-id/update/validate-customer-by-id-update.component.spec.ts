import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ValidateCustomerByIdFormService } from './validate-customer-by-id-form.service';
import { ValidateCustomerByIdService } from '../service/validate-customer-by-id.service';
import { IValidateCustomerById } from '../validate-customer-by-id.model';

import { ValidateCustomerByIdUpdateComponent } from './validate-customer-by-id-update.component';

describe('ValidateCustomerById Management Update Component', () => {
  let comp: ValidateCustomerByIdUpdateComponent;
  let fixture: ComponentFixture<ValidateCustomerByIdUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let validateCustomerByIdFormService: ValidateCustomerByIdFormService;
  let validateCustomerByIdService: ValidateCustomerByIdService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ValidateCustomerByIdUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ValidateCustomerByIdUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ValidateCustomerByIdUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    validateCustomerByIdFormService = TestBed.inject(ValidateCustomerByIdFormService);
    validateCustomerByIdService = TestBed.inject(ValidateCustomerByIdService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const validateCustomerById: IValidateCustomerById = { id: 456 };

      activatedRoute.data = of({ validateCustomerById });
      comp.ngOnInit();

      expect(comp.validateCustomerById).toEqual(validateCustomerById);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IValidateCustomerById>>();
      const validateCustomerById = { id: 123 };
      jest.spyOn(validateCustomerByIdFormService, 'getValidateCustomerById').mockReturnValue(validateCustomerById);
      jest.spyOn(validateCustomerByIdService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ validateCustomerById });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: validateCustomerById }));
      saveSubject.complete();

      // THEN
      expect(validateCustomerByIdFormService.getValidateCustomerById).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(validateCustomerByIdService.update).toHaveBeenCalledWith(expect.objectContaining(validateCustomerById));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IValidateCustomerById>>();
      const validateCustomerById = { id: 123 };
      jest.spyOn(validateCustomerByIdFormService, 'getValidateCustomerById').mockReturnValue({ id: null });
      jest.spyOn(validateCustomerByIdService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ validateCustomerById: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: validateCustomerById }));
      saveSubject.complete();

      // THEN
      expect(validateCustomerByIdFormService.getValidateCustomerById).toHaveBeenCalled();
      expect(validateCustomerByIdService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IValidateCustomerById>>();
      const validateCustomerById = { id: 123 };
      jest.spyOn(validateCustomerByIdService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ validateCustomerById });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(validateCustomerByIdService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
