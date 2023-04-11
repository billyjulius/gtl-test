# GoTo Logistics Take Home Assignment

## Order API

This project contains a simple service with single endpoint `/processOrder`.

---

## Behaviour

It will accept json body contain `Order` object

```
{
    "order_id": 227162,
    "order_description": "sample description",
    "order_status": "New",
    "last_updated_timestamp": "1642321210439",
    "special_order": false
}
```

and will returned `Order` object will updated `status` and `last_updated_timestamp`

---

## Prerequisite

[NodeJS](https://nodejs.org/en/download) v16 or above setup in your local machine

---

## How to Run

- <code>npm install</code>
- <code>npm run start</code>

The server will up as long the terminal is not closed.
In the meantime, you can run the automation test in different terminal
