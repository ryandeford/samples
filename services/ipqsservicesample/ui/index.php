<?php header('Access-Control-Allow-Origin: *'); ?>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <!-- Include Meta Tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <!-- Assign Page Title -->
    <title>IPQS Dashboard</title>
    
    <!-- Include Bootstrap Styles Asset -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    
    <!-- Custom Page CSS -->
    <style media="screen">
      /* TODO */
    </style>
  </head>
  <body>
    <nav id="navbar" class="navbar navbar-dark bg-dark navbar-expand-lg">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">IPQS Dashboard</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <a class="nav-link" href="https://www.ipqualityscore.com/user/dashboard" target="_blank">User Service</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                API Scoring Docs
              </a>
              <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                <?php
                  foreach ([
                    'Email Scoring Docs' => 'https://www.ipqualityscore.com/documentation/email-validation-api/overview',
                    'Phone Scoring Docs' => 'https://www.ipqualityscore.com/documentation/phone-number-validation-api/overview',
                    'URL Scoring Docs' => 'https://www.ipqualityscore.com/documentation/malicious-url-scanner-api/overview',
                    'IP Scoring Docs' => 'https://www.ipqualityscore.com/documentation/proxy-detection-api/overview',
                    'Address Scoring Docs' => 'https://www.ipqualityscore.com/documentation/proxy-detection-api/address-validation',
                  ] as $docName => $docUrl) {
                    echo sprintf('<li><a class="dropdown-item" href="%s" target="_blank">%s</a></li>', $docUrl, $docName);
                  }
                ?>
              </ul>
            </li>
          </ul>
        </div>
        <span id="vln-stores-last-updated-message" class="fst-italic"></span>
      </div>
    </nav>
    
    <div class="container">
      <div class="row text-center">
        <h1 class="m-1 p-1 bg-dark text-white rounded">
          IPQS Service Exploration
        </h1>
      </div>
    </div>
    <div class="container text-center">
      <h2 class="m-1 p-1 bg-secondary text-white rounded">
        API Config
      </h2>
      <div class="row m-1">
        <div class="input-group">
          <span id="label-api-key-token" class="input-group-text">API Key Token</span>
          <input id="input-api-key-token" type="text" class="form-control" placeholder="Enter API Key Token" aria-label="API Key Token" aria-describedby="label-api-key-token">
          <button id="button-save-api-key-token" type="button" class="btn btn-primary">Save</button>
          <button id="button-clear-api-key-token" type="button" class="btn btn-info">Clear</button>
          <button id="button-show-api-key-token" type="button" class="btn btn-success">Show</button>
        </div>
      </div>
      <div class="row text-center m-1">
        <h2 class="m-1 p-1 bg-secondary text-white rounded">
          API Services
        </h2>
        <div class="row mb-1">
          <div class="col">
            <div class="input-group">
              <span id="label-api-scoring-email" class="input-group-text">Email</span>
              <input id="input-api-scoring-email" type="text" class="form-control" placeholder="Enter Email Address" aria-label="Email" aria-describedby="label-api-scoring-email">
              <button id="button-execute-api-scoring-email" type="button" class="btn btn-primary">Calculate</button>
            </div>
          </div>
          <div class="col">
            <div class="input-group">
              <span id="label-api-scoring-phone" class="input-group-text">Phone</span>
              <input id="input-api-scoring-phone" type="text" class="form-control" placeholder="Enter Phone Number" aria-label="Phone" aria-describedby="label-api-scoring-phone">
              <button id="button-execute-api-scoring-phone" type="button" class="btn btn-primary">Calculate</button>
            </div>
          </div>
        </div>
        <div class="row mb-1">
          <div class="col">
            <div class="input-group">
              <span id="label-api-scoring-url" class="input-group-text">URL</span>
              <input id="input-api-scoring-url" type="text" class="form-control" placeholder="Enter URL Endpoint" aria-label="URL" aria-describedby="label-api-scoring-url">
              <button id="button-execute-api-scoring-url" type="button" class="btn btn-primary">Calculate</button>
            </div>
          </div>
          <div class="col">
            <div class="input-group">
              <span id="label-api-scoring-ip" class="input-group-text">IP Address</span>
              <input id="input-api-scoring-ip" type="text" class="form-control" placeholder="Enter IP Address" aria-label="IP" aria-describedby="label-api-scoring-ip">
              <button id="button-execute-api-scoring-ip" type="button" class="btn btn-primary">Calculate</button>
            </div>
          </div>
        </div>
        <div class="row mb-1">
          <div class="col">
            <div class="input-group">
              <span id="label-api-scoring-address" class="input-group-text">Street Address</span>
              <input id="input-api-scoring-address" type="text" class="form-control" placeholder="Enter Street Address" aria-label="Address" aria-describedby="label-api-scoring-address">
              <button id="button-execute-api-scoring-address" type="button" class="btn btn-primary">Calculate</button>
            </div>
          </div>
        </div>
      </div>
      <div class="row text-center m-1">
        <h2 class="m-1 p-1 bg-secondary text-white rounded">
          Calculated Results
        </h2>
        <div class="row mb-1">
          <div class="col">
            <textarea id="textarea-api-scoring-result" class="col-12" rows="10"></textarea>
          </div>
        </div>
    </div>
    
    <!-- Include JQuery JavaScript Asset -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    
    <!-- Include Bootstrap JavaScript Asset -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    
    <!-- Custom Page JavaScript -->
    <script>
      // Once the DOM is Fully Loaded
      $(document).ready(function () {
        const buttonSaveApiKeyToken = document.querySelector('#button-save-api-key-token');
        const buttonClearApiKeyToken = document.querySelector('#button-clear-api-key-token');
        const buttonShowApiKeyToken = document.querySelector('#button-show-api-key-token');
        
        const buttonExecuteApiScoringEmail = document.querySelector('#button-execute-api-scoring-email');
        const buttonExecuteApiScoringPhone = document.querySelector('#button-execute-api-scoring-phone');
        const buttonExecuteApiScoringUrl = document.querySelector('#button-execute-api-scoring-url');
        const buttonExecuteApiScoringIp = document.querySelector('#button-execute-api-scoring-ip');
        const buttonExecuteApiScoringAddress = document.querySelector('#button-execute-api-scoring-address');
        
        const inputApiKeyToken = document.querySelector('#input-api-key-token');
        
        const inputEmail = document.querySelector('#input-api-scoring-email');
        const inputPhone = document.querySelector('#input-api-scoring-phone');
        const inputUrl = document.querySelector('#input-api-scoring-url');
        const inputIp = document.querySelector('#input-api-scoring-ip');
        const inputAddress = document.querySelector('#input-api-scoring-address');
        
        const textareaApiScoringResult = document.querySelector('#textarea-api-scoring-result');
        
        const apiEndpoints = {
          KEY: 'http://localhost:8081/ipqs/api/key',
          EMAIL: 'http://localhost:8081/ipqs/score/email/',
          PHONE: 'http://localhost:8081/ipqs/score/phone/',
          URL: 'http://localhost:8081/ipqs/score/url/',
          IP: 'http://localhost:8081/ipqs/score/ip/',
        };
        
        let apiKeyToken = undefined;
        
        buttonSaveApiKeyToken.onclick = function() {
          let apiKeyTokenValue = inputApiKeyToken.value;
          
          if (/^\s*$/.test(apiKeyTokenValue)) {
            alert('Please provide a valid API Key Token');
            inputApiKeyToken.value = '';
            return;
          }
          
          $.ajax({
            type: 'PUT',
            url: apiEndpoints.KEY,
            contentType: 'application/json',
            data: JSON.stringify({key: apiKeyTokenValue}),
          }).done(function (response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
          
          apiKeyToken = apiKeyTokenValue;
          inputApiKeyToken.value = '';
          inputApiKeyToken.placeholder = 'API Key Token is Set (Click Show to Display It)';
        }
        
        buttonClearApiKeyToken.onclick = function() {
          $.ajax({
            type: 'DELETE',
            url: apiEndpoints.KEY,
          }).done(function (response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
          
          apiKeyToken = undefined;
          inputApiKeyToken.value = '';
          inputApiKeyToken.placeholder = 'Enter API Key Token';
        }
        
        buttonShowApiKeyToken.onclick = function() {          
          $.get(apiEndpoints.KEY, function(response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
        }
        
        buttonExecuteApiScoringEmail.onclick = function() {
          let email = inputEmail.value;
          let emailEndpoint = apiEndpoints.EMAIL + `${email}`;
          
          $.get(emailEndpoint, function(response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
        }
        
        buttonExecuteApiScoringPhone.onclick = function() {
          let phone = inputPhone.value;
          let phoneEndpoint = apiEndpoints.PHONE + `${phone}`;
          
          $.get(phoneEndpoint, function(response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
        }
        
        buttonExecuteApiScoringUrl.onclick = function() {
          let url = inputUrl.value;
          let urlEndpoint = apiEndpoints.URL + `${url}`;
          
          $.get(urlEndpoint, function(response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
        }
        
        buttonExecuteApiScoringIp.onclick = function() {
          let ip = inputIp.value;
          let ipEndpoint = apiEndpoints.IP + `${ip}`;
          
          $.get(ipEndpoint, function(response, status) {
            textareaApiScoringResult.value = JSON.stringify(response);
          })
        }
        
        buttonExecuteApiScoringAddress.onclick = function() {
          textareaApiScoringResult.value = `Result from Street Address Scoring API: ${inputAddress.value}`;
        }
      });
    </script>
  </body>
</html>