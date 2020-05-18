/*
 * Copyright 2020 Park Jun-Hong_(parkjunhong77/google/com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 *
 * This file is generated under this project, "SSH-Tunneling-Service".
 *
 * Date  : 2020. 5. 18. 오후 5:37:20
 *
 * Author: Park_Jun_Hong_(fafanmama_at_naver_com)
 * 
 */

package open.commons.tools.ssh.utils;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.base.Predicate;

/**
 * 
 * @since 2020. 5. 18.
 * @version _._._
 * @author Park_Jun_Hong_(fafanmama_at_naver_com)
 */
public class Utils {
    private Utils() {
    }

    /**
     * 데이터 검증 후 기능을 수행한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     * @param value
     *            데이터
     * @param test
     *            데이터 검증
     * @param consumer
     *            기능 정의 함수
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T> void runIf(T value, Predicate<T> test, Consumer<T> consumer) {
        if (test.apply(value)) {
            consumer.accept(value);
        }
    }
    
    /**
     * 
     * <br>
     * <pre>
     * [개정이력]
     *      날짜    	| 작성자	|	내용
     * ------------------------------------------
     * 2020. 5. 18.		박준홍			최초 작성
     * </pre>
     *
     * @param <T>
     * @param <U>
     * @param param1
     * @param test
     * @param param2
     * @param test2
     * @param consumer
     *
     * @since 2020. 5. 18.
     * @version _._._
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T, U> void runIf(T param1, Predicate<T> test, U param2, Predicate<U> test2, BiConsumer<T, U> consumer) {
        if (test.apply(param1) && test2.apply(param2)) {
            consumer.accept(param1, param2);
        }
    }

    /**
     * 테스트 값을 검증한 후, 실행 결과를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     *            테스트 데이터 타입
     * @param <R>
     *            반환 데이터 타입
     * @param value
     *            테스트 값
     * @param test
     *            테스트 함수
     * @param action
     *            기능 함수
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T, R> R runIf(T value, Predicate<T> test, Function<T, R> action) {
        return runIf(value, test, action, () -> null);
    }

    /**
     * 테스트 값을 검증한 후, 실행 결과를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     *            테스트 데이터 타입
     * @param <R>
     *            반환 데이터 타입
     * @param value
     *            테스트 값
     * @param test
     *            테스트 함수
     * @param action
     *            기능 함수
     * @param defaultValue
     *            기본값
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T, R> R runIf(T value, Predicate<T> test, Function<T, R> action, Supplier<R> defaultValue) {
        if (test.apply(value)) {
            return action.apply(value);
        } else {
            return defaultValue.get();
        }
    }

    /**
     * 검증 내용이 통과한 경우 기능을 실행한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     * @param <U>
     * @param value
     *            테스트 값
     * @param test
     *            테스트 함수
     * @param provider
     *            데이터 제공자
     * @param consumer
     *            데이터 소비자
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T, U> void runIf(T value, Predicate<T> test, Function<T, U> provider, Consumer<U> consumer) {
        if (test.apply(value)) {
            consumer.accept(provider.apply(value));
        }
    }

    /**
     * 테스트 값을 검증한 후, 실행 결과를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     *            테스트 데이터 타입
     * @param <R>
     *            반환 데이터 타입
     * @param value
     *            테스트 값
     * @param test
     *            테스트 함수
     * @param provider
     *            기능함수 파라미터 제공자
     * @param action
     *            기능 함수
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T, U, R> R runIf(T value, Predicate<T> test, Function<T, U> provider, Function<U, R> action) {
        return runIf(value, test, provider, action, () -> null);
    }

    /**
     * 테스트 값을 검증한 후, 실행 결과를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     *            테스트 데이터 타입
     * @param <R>
     *            반환 데이터 타입
     * @param value
     *            테스트 값
     * @param test
     *            테스트 함수
     * @param provider
     *            기능함수 파라미터 제공자
     * @param action
     *            기능 함수
     * @param defaultValue
     *            기본값
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T, U, R> R runIf(T value, Predicate<T> test, Function<T, U> provider, Function<U, R> action, Supplier<R> defaultValue) {
        if (test.apply(value)) {
            return action.apply(provider.apply(value));
        } else {
            return defaultValue.get();
        }
    }

    /**
     * 검증을 통과한 경우 기능을 실행한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <R>
     * @param <T>
     * @param <U>
     * @param value
     *            테스트 값
     * @param tester
     *            테스트 함수
     * @param param
     *            데이터 제공자를 위한 파라미터.
     * @param provider
     *            데이터 제공자
     * @param consumer
     *            데이터 소비자
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <R, T, U> void runIf(T value, Predicate<T> tester, Supplier<U> param, Function<U, R> provider, Consumer<R> consumer) {
        if (tester.apply(value)) {
            consumer.accept(provider.apply(param.get()));
        }
    }

    /**
     * 데이터 검증 후 기능을 수행한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <R>
     * @param <T>
     * @param <U>
     * @param <V>
     * @param value
     *            검증 대상
     * @param tester
     *            데이터 검증기
     * @param param1
     *            파라미터
     * @param param2
     *            파라미터
     * @param provider
     *            사용될 데이터 생성
     * @param consumer
     *            기능 정의 함수
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <R, T, U, V> void runIf(T value, Predicate<T> tester, Supplier<U> param1, Supplier<V> param2, BiFunction<U, V, R> provider, Consumer<R> consumer) {
        if (tester.apply(value)) {
            consumer.accept(provider.apply(param1.get(), param2.get()));
        }
    }

    /**
     * 실행 후 결과를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.      박준홍         최초 작성
     * </pre>
     *
     * @param <R>
     *            반환 데이터
     * @param actions
     *            실행 기능
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @SuppressWarnings("unchecked")
    public static <R> Optional<R> runOnAsync(Supplier<R>... actions) {
        if (actions == null) {
            throw new NullPointerException();
        }

        return Arrays.asList(actions).parallelStream() //
                // 실행
                .map(s -> s.get()) //
                // 에러 메시지 확인
                .filter(s -> s != null) //
                .findAny();
    }

    /**
     * 실행 후 결과를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.      박준홍         최초 작성
     * </pre>
     *
     * @param <R>
     *            반환 데이터
     * @param actions
     *            실행 기능
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    @SuppressWarnings("unchecked")
    public static <R> Optional<R> runOnSync(Supplier<R>... actions) {
        if (actions == null) {
            throw new NullPointerException();
        }

        return Arrays.asList(actions).stream() //
                // 실행
                .map(s -> s.get()) //
                // 에러 메시지 확인
                .filter(s -> s != null) //
                .findAny();
    }

    /**
     * 주어진 값을 그대로 제공하는 {@link Supplier}를 제공한다. <br>
     * 
     * <pre>
     * [개정이력]
     *      날짜      | 작성자   |   내용
     * ------------------------------------------
     * 2020. 5. 18.     박준홍         최초 작성
     * </pre>
     *
     * @param <T>
     * @param value
     * @return
     *
     * @since 2020. 5. 18.
     * @version
     * @author Park_Jun_Hong_(fafanmama_at_naver_com)
     */
    public static final <T> Supplier<T> supplier(T value) {
        return () -> value;
    }

}
