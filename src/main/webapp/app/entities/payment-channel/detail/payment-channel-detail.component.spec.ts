import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaymentChannelDetailComponent } from './payment-channel-detail.component';

describe('PaymentChannel Management Detail Component', () => {
  let comp: PaymentChannelDetailComponent;
  let fixture: ComponentFixture<PaymentChannelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaymentChannelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paymentChannel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaymentChannelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaymentChannelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paymentChannel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paymentChannel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
