import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllDependentsComponent } from './all-dependents.component';

describe('AllDependentsComponent', () => {
  let component: AllDependentsComponent;
  let fixture: ComponentFixture<AllDependentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllDependentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllDependentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
