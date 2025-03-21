# Environment 
Java : 17
Spring Boot: 3.3.1

### 쿼리 튜닝 결과 EXPLAIN ANALYZE  
한달통계 쿼리  
```sql
explain analyze select date_format(h.instant,'%Y-%m-%d'), sum(h.burst_second),count(*) from hourglass_log h
         where h.user_id = 13000 and h.instant between '2022-01-01 00:00:00' and '2022-01-31 23:59:59'
group by date_format(h.instant,'%Y-%m-%d');  
```  
```
-> Table scan on <temporary>  (actual time=11.7..11.7 rows=31 loops=1)
    -> Aggregate using temporary table  (actual time=11.7..11.7 rows=31 loops=1)
        -> Index range scan on h using hourglass_log__index over (user_id = 13000 AND '2022-01-01 00:00:00.000000' <= instant <= '2022-01-31 23:59:59.000000'), with index condition: ((h.user_id = 13000) and (h.instant between '2022-01-01 00:00:00' and '2022-01-31 23:59:59'))  (cost=3720 rows=3100) (actual time=0.0393..9.09 rows=3100 loops=1)
```  

일년통계 쿼리  
```sql
explain analyze select str_to_date(concat(yearweek(any_value(h.instant)),'Sunday'),'%X%V%W'), sum(h.burst_second) from hourglass_log h
         where h.user_id = 13000 and h.instant between '2022-01-01 00:00:00' and '2022-12-31 23:59:59'
group by yearweek(h.instant);
```  
```
-> Table scan on <temporary>  (actual time=242..242 rows=48 loops=1)
    -> Aggregate using temporary table  (actual time=242..242 rows=48 loops=1)
        -> Index range scan on h using hourglass_log__index over (user_id = 13000 AND '2022-01-01 00:00:00.000000' <= instant <= '2022-12-31 23:59:59.000000'), with index condition: ((h.user_id = 13000) and (h.instant between '2022-01-01 00:00:00' and '2022-12-31 23:59:59'))  (cost=72725 rows=61426) (actual time=45.3..194 rows=32700 loops=1)
```
