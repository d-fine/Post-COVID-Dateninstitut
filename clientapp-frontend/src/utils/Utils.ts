export function parseCSV(csvContent: string, seperator: string = ',') {
  if (typeof csvContent === 'string' || csvContent instanceof String) {
    const lines = csvContent.split('\n');
    const header = lines[0].split(seperator);
    const dataRows = lines.slice(1).map((line) => line.split(seperator));
    return {
      header: header,
      data: dataRows,
    };
  }
}

export function transformToPrimevueDataTable(header: string[], data: string[]) {
  return data.map((row) => {
    return header.reduce(
      (acc, key, index) => {
        acc[key] = row[index];
        return acc;
      },
      {} as Record<string, string>
    );
  });
}
