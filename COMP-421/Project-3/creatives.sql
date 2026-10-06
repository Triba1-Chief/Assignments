CONNECT TO COMP421;

-- Drop trigger first if it already exists
-- You may need to run this separately the first time if the trigger does not exist
DROP TRIGGER trg_payment_success_library;

CREATE TRIGGER trg_payment_success_library
AFTER UPDATE OF status ON Payment
REFERENCING NEW AS N OLD AS O
FOR EACH ROW
WHEN (N.status = 'SUCCESS' AND O.status <> 'SUCCESS')
BEGIN ATOMIC

    INSERT INTO LibraryContains (CID, PID, purchaseDate)
    SELECT
        ord.CID,
        eb.PID,
        TIMESTAMP(N.payDate)
    FROM Orders ord
    JOIN OrderContains oc
        ON oc.orderID = ord.orderID
    JOIN eBook eb
        ON eb.PID = oc.PID
    WHERE ord.orderID = N.orderID
      AND NOT EXISTS (
          SELECT 1
          FROM LibraryContains lc
          WHERE lc.CID = ord.CID
            AND lc.PID = eb.PID
      );

END;



--------------------------------------------
-- Test setup for trigger

-- New order for customer 2 buying eBook 1003
INSERT INTO Orders (orderID, CID, orderDate, status, totalAmount)
VALUES (5010, 2, DATE('2026-02-01'), 'PENDING', 18.00);

INSERT INTO OrderContains (orderID, PID)
VALUES (5010, 1003);

INSERT INTO Payment (paymentID, orderID, amount, status, payDate)
VALUES (9010, 5010, 18.00, 'FAILED', DATE('2026-02-01'));

-- Before update: should not be in library
SELECT *
FROM LibraryContains
WHERE CID = 2 AND PID = 1003;

-- Trigger fires here
UPDATE Payment
SET status = 'SUCCESS'
WHERE paymentID = 9010;

-- After update: should now be in library
SELECT *
FROM LibraryContains
WHERE CID = 2 AND PID = 1003;
