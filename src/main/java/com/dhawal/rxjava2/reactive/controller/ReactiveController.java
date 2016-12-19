/**
 * 
 */
package com.dhawal.rxjava2.reactive.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.dhawal.async.pojo.Deal;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author dhawal
 *
 */
@Controller
@RequestMapping("/reactive")
public class ReactiveController {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE,value = "/deferred")
	public @ResponseBody DeferredResult<String> postMessge(@RequestBody Deal inputDeal) {		
		System.out.println("Request Brought By thread = " + Thread.currentThread().getName());
		DeferredResult<String> deferredResult = new DeferredResult<>(2000L,"I have timed Out!!!");
		Observable<String> observable = Observable.just(inputDeal.getName()).subscribeOn(Schedulers.io());
		observable.subscribe((String name) -> {
			Thread.sleep(2000L);
			System.out.println("Thread used for processing = " + Thread.currentThread().getName());
			deferredResult.setResult(name+ " = I have processed Successfully");
		});
		System.out.println("I am leaving the method!! = " + Thread.currentThread().getName());
		return deferredResult;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/observable")
	public @ResponseBody Observable<String> postMessgeObservable(@RequestBody Map<String, String> map) {
		System.out.println("Request Brought By thread = " + Thread.currentThread().getName());
		Observable<String> observable = Observable.just(map.get("name")).subscribeOn(Schedulers.io());
		System.out.println("I am leaving the method!! = " + Thread.currentThread().getName());
		return observable;
	}

}
