import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BcgVacPageComponent } from './bcg-vac-page.component';

describe('BcgVacPageComponent', () => {
  let component: BcgVacPageComponent;
  let fixture: ComponentFixture<BcgVacPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BcgVacPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BcgVacPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
