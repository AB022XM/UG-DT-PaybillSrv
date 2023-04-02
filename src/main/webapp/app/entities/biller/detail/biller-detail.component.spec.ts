import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BillerDetailComponent } from './biller-detail.component';

describe('Biller Management Detail Component', () => {
  let comp: BillerDetailComponent;
  let fixture: ComponentFixture<BillerDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BillerDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ biller: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BillerDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BillerDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load biller on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.biller).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
