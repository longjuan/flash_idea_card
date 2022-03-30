package top.zway.fic.web.exception;

import org.springframework.validation.BindingResult;

/**
 * @author ZZJ
 */
public class Jsr303Checker {
    public static void check(BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            bindingResult.getFieldErrors().forEach(item->{
                sb.append(item.getDefaultMessage()).append("\n");
            });
            throw new Jsr303ValidException(sb.toString().trim());
        }
    }
}
