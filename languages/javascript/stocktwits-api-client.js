'use strict';

const readline = require('readline')
const axios = require('axios');

const commandSchema = `
Available Command Schema

<action> <resource> <identifier> <value/term>

get user <username or id>
get symbol <symbol or id>
get message <message id>

list blocks <username or id>
list mutes <username or id>
list followers <username or id>
list follows <username or id>
list likes <username or id>
list watchlist <username or id>

list messages user <username or id>
list messages symbol <symbol or id>
list messages topic <topic or id>
`;

String.prototype.format = function() {

  let currentString = this;

  for (const argumentIndex in arguments) {
    let regex = new RegExp(`\\{${argumentIndex}\\}`, 'g');
    currentString = currentString.replace(regex, arguments[argumentIndex]);
  }

  return currentString;

}

function stringify(obj) {
  return JSON.stringify(obj, null, 2);
}

const config = {
  debug: false,

  targetAPIEndpoint: 'https://api.stocktwits.com/api/2/',
  targetAPIAuthToken: 'OAuth 3634b7a97642a6829e9db3e11199b3b149c8c818',

  targetFilter: 'all', // filters: all, top, links, charts
  targetLimit: 20, // pagination size: defaults to 30, but the API typically uses 20

  targetRequestTimeout: 5000,
};

axios.interceptors.request.use(

  request => {

    if (request.url.match(/\/(streams|graph)\//i)) {
      if (!request.params) {
        request.params = {};
      }

      if (!('filter' in request.params)) {
        request.params['filter'] = config.targetFilter;
      }

      if (!('limit' in request.params)) {
        request.params['limit'] = config.targetLimit;
      }
    }

    if (request.method.match(/^post$/i) || request.url.match(/\/es_blocking(_and_muting)?\//i)) {
      if (!request.headers) {
        request.headers = {};
      }

      request.headers['Authorization'] = config.targetAPIAuthToken;
    }

    if (!request.timeout) {
      request.timeout = config.targetRequestTimeout;
    }

    if (config.debug) {
      console.debug(stringify(request));
    }

    return request;

  }

);

async function interactiveConsole() {

  async function processCommand(command) {

    try {

      if (command.trim().match(/^help$/i)) {
        console.info(commandSchema);
        input.prompt();
        return;
      }
      else if (command.trim().match(/^q|quit|exit|done|bye$/i)) {
        console.info('Exiting...');
        process.exit();
      }

      let commandTokens = command.trim().split(/\s+/);

      if (commandTokens.length < 3) {
        throw 'Unable to process command, expected format: <action> <resource> <identifier> <value/term>';
      }

      let commandAction = commandTokens[0].toLowerCase();
      let commandResource = commandTokens[1].toLowerCase();
      let commandResourceIdentifier = commandTokens[2];
      let commandValue = commandTokens.length >= 4 ? commandTokens[3] : null;

      switch (commandAction) {
        case 'get':
          switch (commandResource) {
            case 'user':
              let user = await getUser(commandResourceIdentifier);
              console.info(stringify(user));
              break;
            case 'symbol':
              let symbol = await getSymbol(commandResourceIdentifier);
              console.info(stringify(symbol));
              break;
            case 'message':
              let message = await getMessage(commandResourceIdentifier);
              console.info(stringify(message));
              break;
            default:
              throw 'Unknown command resource for get action: {0}'.format(commandResource);
          }
          break;
        case 'list':
          switch (commandResource) {
            case 'blocks':
              let userBlocks = await getUserBlocks(commandResourceIdentifier);
              console.info(stringify(userBlocks));
              console.info('Total block relationships for {0}: {1}'.format(commandResourceIdentifier, userBlocks.length));
              break;
            case 'mutes':
              let userMutes = await getUserMutes(commandResourceIdentifier);
              console.info(stringify(userMutes));
              console.info('Total mutes made by {0}: {1}'.format(commandResourceIdentifier, userMutes.length));
              break;
            case 'followers':
              let userFollowers = await getUserFollowers(commandResourceIdentifier);
              console.info(stringify(userFollowers));
              console.info('Total followers of {0}: {1}'.format(commandResourceIdentifier, userFollowers.length));
              break;
            case 'follows':
              let userFollows = await getUserFollows(commandResourceIdentifier);
              console.info(stringify(userFollows));
              console.info('Total follows made by {0}: {1}'.format(commandResourceIdentifier, userFollows.length));
              break;
            case 'likes':
            case 'watchlist':
              // Either getUserLikes or getUserWatchlist depending on command resource
              // Note: This is of course a very unsafe/poor practice, but it's simply to show an example using eval(...) to make a dynamic function calls
              let listedUserContent = await eval(
                'getUser{0}'.format(commandResource.charAt(0).toUpperCase() + commandResource.slice(1))
              )(commandResourceIdentifier);
              console.info(stringify(listedUserContent));
              console.info('Total fetched entries for {0}: {1}'.format(commandResourceIdentifier, listedUserContent.length));
              break;
            case 'messages':
              if (!commandValue) {
                throw 'A command value needs to be supplied for list messages actions: list messages [user|symbol|topic] <value/term>';
              }
              switch(commandResourceIdentifier.toLowerCase()) {
                case 'user':
                  let listedUserMessagesContent = await getMessagesForUser(commandValue);
                  console.info(stringify(listedUserMessagesContent));
                  console.info('Total fetched user messages for {0}: {1}'.format(commandValue, listedUserMessagesContent.length));
                  break;
                case 'symbol':
                  let listedSymbolMessagesContent = await getMessagesForSymbol(commandValue);
                  console.info(stringify(listedSymbolMessagesContent));
                  console.info('Total fetched symbol messages for {0}: {1}'.format(commandValue, listedSymbolMessagesContent.length));
                  break;
                case 'topic':
                  let listedTopicMessagesContent = await getMessagesForTopic(commandValue);
                  console.info(stringify(listedTopicMessagesContent));
                  console.info('Total fetched topic messages for {0}: {1}'.format(commandValue, listedTopicMessagesContent.length));
                  break;
                default:
                  throw 'Invalid command resource identifier supplied for list messages action, expecting user|symbol|topic: {0}'.format(commandResourceIdentifier);
              }
              break;
            default:
              throw 'Unknown command resource for list action: {0}'.format(commandResource);
          }
          break;
        default:
          throw 'Unknown command action: {0}'.format(commandAction);
      }
    } catch (error) {
      if (error.response) {
        console.error('An API error occurred:');
        console.error(stringify(error.response.data));
      }
      else {
        console.error('An error occurred:');
        console.error(error);
      }
    }

    input.prompt();

  }

  let input = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
  });

  input.on('line', processCommand);
  input.prompt();

}

async function getUser(username) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/users/{0}/extended.json'.format(username)
  )).data;

  return response.user ? response.user : response;

}

async function getSymbol(symbol) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/symbols/with_price/{0}.json?extended=true'.format(symbol)
  )).data;

  return response.symbol ? response.symbol : response;

}

async function getMessage(id) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/messages/show/{0}.json'.format(id)
  )).data;

  return response.message ? response.message : response;

}

async function getUserFollowers(username) {

  let users = (await traversePaginatedEntriesForUrl(
    'https://api.stocktwits.com/api/2/graph/followers/{0}.json'.format(username),
    'users'
  )).map(user => user.username);

  return users.sort(Intl.Collator().compare);

}

async function getUserFollows(username) {

  let users = (await traversePaginatedEntriesForUrl(
    'https://api.stocktwits.com/api/2/graph/following/{0}.json'.format(username),
    'users'
  )).map(user => user.username);

  return users.sort(Intl.Collator().compare);

}

async function getUserLikes(username) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/streams/user/liked/{0}.json'.format(username)
  )).data;

  return response.messages ? response.messages : response;

}

async function getUserWatchlist(username) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/watchlists/user/{0}.json'.format((await getUser(username)).id)
  )).data;

  return response.watchlist.symbols ? response.watchlist.symbols : response;

}

async function getUserBlocks(username) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/graph/es_blocking_and_muting/{0}.json'.format(username)
  )).data;

  if (response.blocked_ids) {

    let blockedUsers = [];

    await Promise.all(response.blocked_ids.map(async (id) => {
      try {
        blockedUsers.push((await getUser(id)).username);
      } catch(error) {
        console.error('Unable to fetch blocked user with id: {0}: {1}: {2}'.format(id, error.name, error.message));
      }
    }));

    return blockedUsers.sort(Intl.Collator().compare);

  }
  else {
    return response;
  }

}

async function getUserMutes(username) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/graph/es_blocking_and_muting/{0}.json'.format(username)
  )).data;

  if (response.muted_ids) {

    let mutedUsers = [];

    await Promise.all(response.muted_ids.map(async (id) => {
      try {
        mutedUsers.push((await getUser(id)).username);
      } catch(error) {
        console.error('Unable to fetch muted user with id: {0}: {1}: {2}'.format(id, error.name, error.message));
      }
    }));

    return mutedUsers.sort(Intl.Collator().compare);

  }
  else {
    return response;
  }

}

async function blockUser(username) {

  let response = (await axios.post(
    'https://api.stocktwits.com/api/2/blocks/create/{0}.json'.format((await getUser(username)).id)
  )).data;

  return response.user ? response.user : response;

}

async function unblockUser(username) {

  let response = (await axios.post(
    'https://api.stocktwits.com/api/2/blocks/destroy/{0}.json'.format((await getUser(username)).id)
  )).data;

  return response.user ? response.user : response;

}

async function muteUser(username) {

  let response = (await axios.post(
    'https://api.stocktwits.com/api/2/mutes/create/{0}.json'.format((await getUser(username)).id)
  )).data;

  return response.user ? response.user : response;

}

async function unmuteUser(username) {

  let response = (await axios.post(
    'https://api.stocktwits.com/api/2/mutes/destroy/{0}.json'.format((await getUser(username)).id)
  )).data;

  return response.user ? response.user : response;

}

async function getMessagesForUser(username) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/streams/user/{0}.json'.format(username)
  )).data;

  return response.messages ? response.messages : response;

}

async function getMessagesForSymbol(symbol) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/streams/symbol/{0}.json'.format(symbol)
  )).data;

  return response.messages ? response.messages : response;

}

async function getMessagesForTopic(topic) {

  let response = (await axios.get(
    'https://api.stocktwits.com/api/2/streams/topic/{0}.json'.format(topic)
  )).data;

  return response.messages ? response.messages : response;

}

async function traversePaginatedEntriesForUrl(url, responseKey) {

  let entries = []
  let cursor = null;

  do {

    let response = (await axios.get(
      url,
      cursor ? { params: { max: cursor.max } } : null
    )).data;

    if (responseKey in response) {
      entries.push(...response[responseKey]);
    }
    else {
      throw 'Could not locate expected entries key in response data during pagination traversal: {0}'.format(responseKey);
    }

    if ('cursor' in response) {
      cursor = response.cursor;
    }
    else {
      throw 'Could not locate expected pagination cursor in response data during pagination traversal';
    }

  } while (cursor.more);

  return entries;

}

interactiveConsole();
