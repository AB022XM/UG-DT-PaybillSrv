import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PaybillDetailComponent } from './paybill-detail.component';

describe('Paybill Management Detail Component', () => {
  let comp: PaybillDetailComponent;
  let fixture: ComponentFixture<PaybillDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PaybillDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ paybill: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(PaybillDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(PaybillDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load paybill on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.paybill).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
