package com.hkbu.security.exception;

import com.hkbu.security.vo.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Chet
 * @date 11/2/2023 3:41 pm
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
public class GlobalException extends RuntimeException {

    private ResponseEnum responseEnum;

}
