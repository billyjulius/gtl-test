const express = require('express');
const bodyParser = require('body-parser');

const app = express();

// middleware for parsing JSON
app.use(bodyParser.json());

const apiRouter = express.Router();

apiRouter.post('/processOrder', (req, res) => {
  const order = req.body;

  const requiredProperties = [
    { name: 'order_id', type: 'number' },
    { name: 'order_description', type: 'string' },
    { name: 'order_status', type: 'string' },
    { name: 'last_updated_timestamp', type: 'string' },
    { name: 'special_order', type: 'boolean' },
  ];
  const missingProperties = requiredProperties.filter((prop) => !order.hasOwnProperty(prop.name));
  const invalidProperties = requiredProperties.filter(
    (prop) => order.hasOwnProperty(prop.name) && typeof order[prop.name] !== prop.type
  );

  if (missingProperties.length > 0) {
    return res.status(400).json({
      error: `Missing properties: ${missingProperties.map((prop) => prop.name).join(', ')}`,
    });
  }

  if (invalidProperties.length > 0) {
    return res.status(400).json({
      error: `Invalid data types: ${invalidProperties.map((prop) => prop.name).join(', ')}`,
    });
  }

  // update order status and last updated timestamp
  order.order_status = 'Processing';
  order.last_updated_timestamp = new Date().getTime();

  res.json(order);
});

// route for processing orders
app.use('/api', apiRouter);

// start the server
const port = 3000;
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
