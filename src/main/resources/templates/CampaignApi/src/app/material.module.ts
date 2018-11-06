import { NgModule } from '@angular/core';
import {MatButtonModule, MatCheckboxModule} from '@angular/material';
import {MatTabsModule} from '@angular/material/tabs';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';
import {MatMenuModule} from '@angular/material/menu';
import {MatToolbarModule} from '@angular/material/toolbar';


@NgModule({
  imports:
  [MatButtonModule,
     MatCheckboxModule,
     MatFormFieldModule,
     MatIconModule,
     MatInputModule,
     MatSelectModule,
     MatTabsModule,
     MatToolbarModule,
     MatMenuModule
    ],
  exports:
  [MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    MatTabsModule,
    MatToolbarModule,
    MatMenuModule
  ],
})

export class MaterialModule { }