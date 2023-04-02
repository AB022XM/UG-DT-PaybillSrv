import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AssociatedFeesFormService } from './associated-fees-form.service';
import { AssociatedFeesService } from '../service/associated-fees.service';
import { IAssociatedFees } from '../associated-fees.model';
import { IPayment } from 'app/entities/payment/payment.model';
import { PaymentService } from 'app/entities/payment/service/payment.service';

import { AssociatedFeesUpdateComponent } from './associated-fees-update.component';

describe('AssociatedFees Management Update Component', () => {
  let comp: AssociatedFeesUpdateComponent;
  let fixture: ComponentFixture<AssociatedFeesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let associatedFeesFormService: AssociatedFeesFormService;
  let associatedFeesService: AssociatedFeesService;
  let paymentService: PaymentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AssociatedFeesUpdateComponent],
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
      .overrideTemplate(AssociatedFeesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssociatedFeesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    associatedFeesFormService = TestBed.inject(AssociatedFeesFormService);
    associatedFeesService = TestBed.inject(AssociatedFeesService);
    paymentService = TestBed.inject(PaymentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Payment query and add missing value', () => {
      const associatedFees: IAssociatedFees = { id: 456 };
      const payment: IPayment = { id: 17776 };
      associatedFees.payment = payment;

      const paymentCollection: IPayment[] = [{ id: 47279 }];
      jest.spyOn(paymentService, 'query').mockReturnValue(of(new HttpResponse({ body: paymentCollection })));
      const additionalPayments = [payment];
      const expectedCollection: IPayment[] = [...additionalPayments, ...paymentCollection];
      jest.spyOn(paymentService, 'addPaymentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      expect(paymentService.query).toHaveBeenCalled();
      expect(paymentService.addPaymentToCollectionIfMissing).toHaveBeenCalledWith(
        paymentCollection,
        ...additionalPayments.map(expect.objectContaining)
      );
      expect(comp.paymentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const associatedFees: IAssociatedFees = { id: 456 };
      const payment: IPayment = { id: 63748 };
      associatedFees.payment = payment;

      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      expect(comp.paymentsSharedCollection).toContain(payment);
      expect(comp.associatedFees).toEqual(associatedFees);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociatedFees>>();
      const associatedFees = { id: 123 };
      jest.spyOn(associatedFeesFormService, 'getAssociatedFees').mockReturnValue(associatedFees);
      jest.spyOn(associatedFeesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: associatedFees }));
      saveSubject.complete();

      // THEN
      expect(associatedFeesFormService.getAssociatedFees).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(associatedFeesService.update).toHaveBeenCalledWith(expect.objectContaining(associatedFees));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociatedFees>>();
      const associatedFees = { id: 123 };
      jest.spyOn(associatedFeesFormService, 'getAssociatedFees').mockReturnValue({ id: null });
      jest.spyOn(associatedFeesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ associatedFees: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: associatedFees }));
      saveSubject.complete();

      // THEN
      expect(associatedFeesFormService.getAssociatedFees).toHaveBeenCalled();
      expect(associatedFeesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssociatedFees>>();
      const associatedFees = { id: 123 };
      jest.spyOn(associatedFeesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ associatedFees });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(associatedFeesService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePayment', () => {
      it('Should forward to paymentService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(paymentService, 'comparePayment');
        comp.comparePayment(entity, entity2);
        expect(paymentService.comparePayment).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
