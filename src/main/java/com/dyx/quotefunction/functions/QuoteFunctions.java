package com.dyx.quotefunction.functions;

import com.dyx.quotefunction.domain.Quote;
import com.dyx.quotefunction.domain.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class QuoteFunctions {
    @Bean
    Supplier<Flux<Quote>> allQuotes(QuoteService quoteService){
        return ()->{
            log.info("Getting all quotes");
            return Flux.fromIterable(quoteService.getAllQuotes())
                    //每次1个元素，每个元素之间间隔1秒
                    .delaySequence(Duration.ofSeconds(1));
        };
    }

    @Bean
    Supplier<Quote> randomQuote(QuoteService quoteService){
        return () ->{
            log.info("Getting random quote");
            return quoteService.getRandomQuote();
        };
    }

    @Bean
    Consumer<Quote> logQuote(){
        return quote -> {
            log.info("Quote: '{}' by {}",quote.content(),quote.author());
        };
    }
}
