import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor } from '@angular/common/http';
import { HttpHandler, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../token/token.service';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
  constructor(
    private tokenService: TokenService) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const token = this.tokenService.token;

    if (req.url.endsWith('/auth/register')) {
      return next.handle(req); // 不添加令牌，直接处理请求
    }

    if (req.url.endsWith('/auth/activate-account')) {
      return next.handle(req); // 不添加令牌，直接处理请求
    }

    if (req.url.endsWith('/auth/authenticate')) {
      return next.handle(req); // 不添加令牌，直接处理请求
    }


    // if (req.url.endsWith('/books')) {
    //   return next.handle(req); // 不添加令牌，直接处理请求
    // }




    if (token) {
      const authReq = req.clone({
        headers: new HttpHeaders({
          Authorization: `Bearer ${token}`
        })
      });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}
// export const httpTokenInterceptor: HttpInterceptorFn = (req, next) => {
//   return next(req);
// };
