import { Component, OnInit } from '@angular/core';
import { RegistroCampanyaService } from '../_services/registroCampanya.service';
import { RegistroCampanyaDto } from '../_dto/registroCampanya.dto';

@Component({
  selector: 'app-registroCampanya',
  templateUrl: './registroCampanya.component.html',
  styleUrls: ['./registroCampanya.component.css']
})
export class RegistroCampanyaComponent implements OnInit {

  nombreCampanya: string;
  codigo: string;

  constructor(private registroCampanyaService: RegistroCampanyaService) { }

  ngOnInit() {
  }

  doRegistroCampanya() {
    
    const registroCampanyaDto = new RegistroCampanyaDto(this.nombreCampanya, this.codigo);
    this.registroCampanyaService.registroCampanya(registroCampanyaDto).subscribe(registroCampanyaResp => {
      console.log('Campaña creada');
    }, error => {
      console.log('Error en petición de registro');
    }
    );
}

validarCampoVacio(){

  if (this.nombreCampanya!='' || this.codigo!='') {
    this.doRegistroCampanya();
  }else{
    console.log('Hay algún campo vacío.');
  }  
}
}