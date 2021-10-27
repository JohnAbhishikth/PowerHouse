import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DependentDashboardHomeComponent } from './dependent-dashboard-home.component';

describe('DependentDashboardHomeComponent', () => {
  let component: DependentDashboardHomeComponent;
  let fixture: ComponentFixture<DependentDashboardHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DependentDashboardHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DependentDashboardHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
