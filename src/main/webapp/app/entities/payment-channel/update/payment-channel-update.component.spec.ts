import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PaymentChannelFormService } from './payment-channel-form.service';
import { PaymentChannelService } from '../service/payment-channel.service';
import { IPaymentChannel } from '../payment-channel.model';

import { PaymentChannelUpdateComponent } from './payment-channel-update.component';

describe('PaymentChannel Management Update Component', () => {
  let comp: PaymentChannelUpdateComponent;
  let fixture: ComponentFixture<PaymentChannelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let paymentChannelFormService: PaymentChannelFormService;
  let paymentChannelService: PaymentChannelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PaymentChannelUpdateComponent],
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
      .overrideTemplate(PaymentChannelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PaymentChannelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    paymentChannelFormService = TestBed.inject(PaymentChannelFormService);
    paymentChannelService = TestBed.inject(PaymentChannelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const paymentChannel: IPaymentChannel = { id: 456 };

      activatedRoute.data = of({ paymentChannel });
      comp.ngOnInit();

      expect(comp.paymentChannel).toEqual(paymentChannel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentChannel>>();
      const paymentChannel = { id: 123 };
      jest.spyOn(paymentChannelFormService, 'getPaymentChannel').mockReturnValue(paymentChannel);
      jest.spyOn(paymentChannelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentChannel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentChannel }));
      saveSubject.complete();

      // THEN
      expect(paymentChannelFormService.getPaymentChannel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(paymentChannelService.update).toHaveBeenCalledWith(expect.objectContaining(paymentChannel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentChannel>>();
      const paymentChannel = { id: 123 };
      jest.spyOn(paymentChannelFormService, 'getPaymentChannel').mockReturnValue({ id: null });
      jest.spyOn(paymentChannelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentChannel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: paymentChannel }));
      saveSubject.complete();

      // THEN
      expect(paymentChannelFormService.getPaymentChannel).toHaveBeenCalled();
      expect(paymentChannelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPaymentChannel>>();
      const paymentChannel = { id: 123 };
      jest.spyOn(paymentChannelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ paymentChannel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(paymentChannelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
