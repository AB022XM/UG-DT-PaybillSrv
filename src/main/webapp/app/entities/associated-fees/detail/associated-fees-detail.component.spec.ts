import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AssociatedFeesDetailComponent } from './associated-fees-detail.component';

describe('AssociatedFees Management Detail Component', () => {
  let comp: AssociatedFeesDetailComponent;
  let fixture: ComponentFixture<AssociatedFeesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssociatedFeesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ associatedFees: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(AssociatedFeesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssociatedFeesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load associatedFees on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.associatedFees).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
