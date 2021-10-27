import { Injectable, Injector } from '@angular/core';
import { HttpHandler, HttpInterceptor, HttpRequest, HttpResponse, HttpEvent, HttpHeaders } from '@angular/common/http'
import { LoginService } from './login.service';
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

  token!: string
  constructor(private injector: Injector, private loginService: LoginService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {

    this.loginService.token.subscribe(data => {
      this.token = data
    })
    // alert('token from interceptor : ' + this.token)

    if (req.headers.has('NA')) {
      const newHeaders = req.headers.delete('NA')
      const newRequest = req.clone({ headers: newHeaders })
      return next.handle(newRequest)
    } else {
      let tokenizedRequest = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + this.token
        }
      })
      return next.handle(tokenizedRequest)
      //   .pipe(
      //     map((event: HttpEvent<any>) => {
      //       if (event instanceof HttpResponse) {
      //         // event.clone({
      //         console.log(event.headers)
      //         event.headers.append('cache-control', 'no-cache, must-revalidate')
      //         event.headers.append('pragma', 'no-cache')
      //         const keys = event.headers.keys()

      //         keys.forEach(key => {
      //           console.log('headers and responses')
      //           console.log(key + ' : ' + event.headers.get(key))
      //         })
      //       }
      //       return event
      //     })
      //   )
    }
  }
}