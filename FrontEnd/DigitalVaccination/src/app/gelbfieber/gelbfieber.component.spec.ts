import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GelbfieberComponent } from './gelbfieber.component';

describe('GelbfieberComponent', () => {
  let component: GelbfieberComponent;
  let fixture: ComponentFixture<GelbfieberComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GelbfieberComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GelbfieberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
