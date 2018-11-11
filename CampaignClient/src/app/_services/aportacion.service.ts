import { environment } from "src/environments/environment.prod";
import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from "./auth.service";
import { AportacionCreateResponse } from "../_interfaces/aportacion-create-response.interface";
import { AportacionCreateDto } from "../_dto/aportacion-create.dto";
import { Observable } from "rxjs";

const aportacionUrl = `${environment.apiUrl}/aportaciones`;

@Injectable({
  providedIn: 'root'
})
export class AportacionService {

  constructor(private http: HttpClient, private authService: AuthService) { }

  createAportacion(aportacionCreateDto: AportacionCreateDto): Observable<AportacionCreateResponse> {
    const requestOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${this.authService.getToken()}`
      })
    };

    return this.http.post<AportacionCreateResponse>(`${aportacionUrl}/nuevaAportacion`, aportacionCreateDto, requestOptions);
  }
}
