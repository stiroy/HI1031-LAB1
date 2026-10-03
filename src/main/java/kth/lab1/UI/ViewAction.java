package kth.lab1.UI;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@FunctionalInterface 
public interface ViewAction {
    /*
    *   Handles an incoming action route.
    *   @returns viewname
    */
    //TODO: add correct exeption
    //  research, security consernse
    //  Reason, by decoppeling the conneciton between url requests and function 
    //  rapid development becomes possiable though making functions that yet doesnt exist
    String execute(HttpServletRequest request, HttpServletResponse response);
}
