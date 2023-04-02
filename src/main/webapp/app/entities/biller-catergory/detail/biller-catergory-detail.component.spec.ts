import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BillerCatergoryDetailComponent } from './biller-catergory-detail.component';

describe('BillerCatergory Management Detail Component', () => {
  let comp: BillerCatergoryDetailComponent;
  let fixture: ComponentFixture<BillerCatergoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BillerCatergoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ billerCatergory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(BillerCatergoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(BillerCatergoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load billerCatergory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.billerCatergory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
