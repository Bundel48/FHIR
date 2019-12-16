import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TubVacPageComponent } from './tub-vac-page.component';

describe('TubVacPageComponent', () => {
  let component: TubVacPageComponent;
  let fixture: ComponentFixture<TubVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TubVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TubVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
