const localStorageWrapper = {
  set: (key, value) => localStorage.setItem(key, JSON.stringify(value)),
  get: key => {
    const value = localStorage.getItem(key);

    return value ? JSON.parse(value) : value;
  },
};

const slugify = str =>
  str
    .toString()
    .toLowerCase()
    .replace(/\s+/g, '-')
    .replace(/[^\w-]+/g, '')
    .replace(/--+/g, '-')
    .trim();

const dateValid = (date) => {
  var patternData = /^[0-9]{2}-[0-9]{2}-[0-9]{4}$/;
  return patternData.test(date)
}

export {slugify, localStorageWrapper, dateValid};
