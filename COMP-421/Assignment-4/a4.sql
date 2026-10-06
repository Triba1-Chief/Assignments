--Q1.
SELECT DISTINCT E.url
FROM Evaluation E
WHERE E.grade IN ('good', 'ok')
ORDER BY E.url
;


--Q2.
SELECT DISTINCT W.url, W.title
FROM Topics T, Links L, Webpages W
WHERE T.name = 'computer science'
  AND L.topicid = T.topicid
  AND W.url = L.url
ORDER BY W.url
;


--Q3.
SELECT DISTINCT W.url, W.title
FROM Topics T, Links L, Webpages W
WHERE L.topicid = T.topicid
  AND W.url = L.url
  AND T.name = W.title
ORDER BY W.url
;


--Q4.
SELECT W.url, W.title
FROM Webpages W
WHERE NOT EXISTS (
  SELECT 1
  FROM Evaluation E
  WHERE E.url = W.url
    AND E.evaldate <= DATE '2020-12-31'
)
ORDER BY W.url
;


--Q5.
SELECT W.url, W.title
FROM Webpages W
WHERE EXISTS (
  SELECT 1
  FROM Evaluation E1
  WHERE E1.url = W.url
    AND E1.email = 'x@a.ca'
)
AND EXISTS (
  SELECT 1
  FROM Evaluation E2
  WHERE E2.url = W.url
    AND E2.email = 'a@x.ca'
)
ORDER BY W.url
;


--Q6.
SELECT X.url
FROM (
  (SELECT DISTINCT E.url
   FROM Evaluation E
   WHERE E.email = 'x@a.ca')
  EXCEPT
  (SELECT DISTINCT E.url
   FROM Evaluation E
   WHERE E.email <> 'x@a.ca')
) X
ORDER BY X.url
;


--Q7.
SELECT DISTINCT W.url, W.title
FROM Links L1, Links L2, Webpages W
WHERE L1.url = L2.url
  AND L1.topicid <> L2.topicid
  AND W.url = L1.url
ORDER BY W.url
;


--Q8.
SELECT T.topicid, T.name
FROM Topics T
WHERE NOT EXISTS (
  SELECT 1
  FROM Webpages W
  WHERE NOT EXISTS (
    SELECT 1
    FROM Links L
    WHERE L.topicid = T.topicid
      AND L.url = W.url
  )
)
ORDER BY T.topicid
;


--Q9.
SELECT COUNT(*) numlinks
FROM Links
;