
export default function authHeader() {

  if (user && user.token) {
    return {Authorization: 'Bearer ' + user.token};
  } else {
    return {};
  }
}
