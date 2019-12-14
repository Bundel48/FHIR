import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RubellaVacPageComponent } from './rubella-vac-page.component';

describe('RubellaVacPageComponent', () => {
  let component: RubellaVacPageComponent;
  let fixture: ComponentFixture<RubellaVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RubellaVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RubellaVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
