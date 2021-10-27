import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateParentComponent } from './create-parent.component';

describe('CreateParentComponent', () => {
  let component: CreateParentComponent;
  let fixture: ComponentFixture<CreateParentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateParentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateParentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
