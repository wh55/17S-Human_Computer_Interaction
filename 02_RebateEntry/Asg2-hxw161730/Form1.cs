using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.IO;
using System.Text.RegularExpressions;

namespace Asg2_hxw161730
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            InitializeDates();
            textBox3.CharacterCasing = CharacterCasing.Upper; //middle initial
            textBox7.CharacterCasing = CharacterCasing.Upper; //state            
        }

        public void InitializeDates()
        {
            var today = DateTime.Today.ToString("MM/dd/yyyy");
            textBox11.Text = today;
        }

        public void ClearFields()
        {
            textBox1.Text = String.Empty;
            textBox2.Text = String.Empty;
            textBox3.Text = String.Empty;
            textBox4.Text = String.Empty;
            textBox5.Text = String.Empty;
            textBox6.Text = String.Empty;
            textBox7.Text = String.Empty;
            textBox8.Text = String.Empty;
            textBox9.Text = String.Empty;
            textBox10.Text = String.Empty;
            radioButton1.Checked = false;
            radioButton2.Checked = false;
        }

        public void LoadFile()
        {
            listView1.Items.Clear();
            if (!File.Exists("CS6326Asg2.txt"))
            {
                var myFile = File.Create("CS6326Asg2.txt");
                myFile.Close();
                MessageBox.Show("The file is empty. :)");
            }
            else
            {
                StreamReader sr = new StreamReader("CS6326Asg2.txt");

                while (sr.Peek() > -1)
                {
                    string s = sr.ReadLine();
                    string[] element = s.Split('\t');
                    ListViewItem item = new ListViewItem(element[0] + " " + element[2] + " " + element[1]);
                    for (int i = 0; i < 15; i++)
                        item.SubItems.Add(element[i]);
                    listView1.Items.Add(item); //totally 16 columns --full name + 12 + 2timestamps+backspace
                }
                sr.Close();
            }
        }

        private DateTime clickTime1;
        private DateTime clickTime2;
        private int backspace = 0;     

        public bool checkFullname()
        {
            if (!File.Exists("CS6326Asg2.txt"))
            {
                var myFile = File.Create("CS6326Asg2.txt");
                myFile.Close();
            }
            
        
                string[] filelines = File.ReadAllLines("CS6326Asg2.txt");
                string search = textBox1.Text + "\t" + textBox2.Text + "\t" + textBox3.Text;
                int i;
                bool found = false;
                for (i = 0; i < filelines.Length && !found; i++)
                {
                    if (filelines[i].Contains(search))
                        found = true;
                }
                return found;
            
        }

        private void Form1_KeyPress(object sender, KeyPressEventArgs e)
        {
           // if (e.KeyChar == (char)Keys.Back)
              //  backspace++;
        }

        private void button1_Click(object sender, EventArgs e) //save btn
        {
            clickTime2 = DateTime.Now;
            
            string[] contents = new string[15];
            contents[0] = textBox1.Text;
            contents[1] = textBox2.Text;
            contents[2] = textBox3.Text;
            contents[3] = textBox4.Text;
            contents[4] = textBox5.Text;
            contents[5] = textBox6.Text;
            contents[6] = textBox7.Text;
            contents[7] = textBox8.Text;
            contents[8] = textBox9.Text;
            contents[9] = textBox10.Text;
            contents[10] = null;
            contents[11] = textBox11.Text;
            contents[12] = clickTime1.ToString("hh:mm:ss");
            contents[13] = clickTime2.ToString("hh:mm:ss");
            contents[14] = backspace.ToString();                  
            if (radioButton1.Checked && !radioButton2.Checked)
                contents[10] = "YES";
            if (!radioButton1.Checked && radioButton2.Checked)
                contents[10] = "NO";

            //email validation
            string strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" + @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" + @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
            Regex re = new Regex(strRegex);

            //date validation
            DateTime dr;

            if (string.IsNullOrWhiteSpace(contents[0]) || string.IsNullOrWhiteSpace(contents[1]) == true)
                MessageBox.Show("Please complete the name.  :)");
            else if (string.IsNullOrWhiteSpace(contents[8]) == true)
                MessageBox.Show("Please enter a phone number.  :)");
            else if (this.checkFullname())
                MessageBox.Show("Please enter a different name.  :)");
            else if (!re.IsMatch(textBox10.Text)&&!string.IsNullOrWhiteSpace(textBox10.Text) && (textBox10.Text != "unk."))
                MessageBox.Show("Please enter a valid e-mail address.  :)");
            else if (!DateTime.TryParse(textBox11.Text, out dr))
                MessageBox.Show("Please enter a valid date MM/DD/YYYY.  :)");
            else
            {
                if (!File.Exists("CS6326Asg2.txt"))
                {
                    var myFile = File.Create("CS6326Asg2.txt");
                    myFile.Close();
                }
                StreamWriter sw = new StreamWriter("CS6326Asg2.txt", true);
                for (int i = 0; i < 15; i++)
                {
                    if (string.IsNullOrWhiteSpace(contents[i]))
                        contents[i] = "unk.";  //unknown

                    sw.Write(contents[i] + "\t");
                }
                sw.Write(Environment.NewLine);
                sw.Close();

                this.LoadFile();
                listView1.Items[listView1.Items.Count - 1].EnsureVisible(); //scroll down to see the newly added
            }
            backspace = 0;
        }

        private void button4_Click(object sender, EventArgs e) //load btn
        {
            this.LoadFile();
        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {
            if (listView1.SelectedItems.Count == 1)
            {
                if (radioButton1.Checked)
                    radioButton1.Checked = false;
                if (radioButton2.Checked)
                    radioButton2.Checked = false;
                ListViewItem item = listView1.SelectedItems[0];
                textBox1.Text = item.SubItems[1].Text;  //first name
                textBox2.Text = item.SubItems[2].Text;  //last name
                textBox3.Text = item.SubItems[3].Text;  //middle name
                textBox4.Text = item.SubItems[4].Text;  //address line1
                textBox5.Text = item.SubItems[5].Text;  //address line2
                textBox6.Text = item.SubItems[6].Text;  //city
                textBox7.Text = item.SubItems[7].Text;  //state
                textBox8.Text = item.SubItems[8].Text;  //zip code
                textBox9.Text = item.SubItems[9].Text;   //phone number
                textBox10.Text = item.SubItems[10].Text; //email address
                textBox11.Text = item.SubItems[12].Text; //date
                if (item.SubItems[11].Text == "YES")     //attachment
                    radioButton1.PerformClick();
                if (item.SubItems[11].Text == "NO")
                    radioButton2.PerformClick();
            }
        }

        private void button2_Click(object sender, EventArgs e) //clear fields btn
        {
            this.ClearFields();
        }

        private void button3_Click(object sender, EventArgs e) //delete btn
        {
            if (listView1.SelectedItems.Count != 1)
                MessageBox.Show("Please select only one line from the table to delete.  :)");


            if (listView1.SelectedItems.Count == 1)
            {
                string[] filelines = File.ReadAllLines("CS6326Asg2.txt");
                string[] newfilelines = new string[filelines.Length - 1];
                ListViewItem item = listView1.SelectedItems[0];
                string search = item.SubItems[1].Text + "\t" + item.SubItems[2].Text + "\t" + item.SubItems[3].Text;                

                bool found = false;
                int i;
                for (i = 0; i < filelines.Length && !found; i++)
                {
                    if (filelines[i].Contains(search))
                       found = true;
                    if(i!=filelines.Length-1)
                       newfilelines[i] = filelines[i];
                }

                if (!found)
                    return;

                int j;
                for (j = i; j < filelines.Length; j++)
                {
                    newfilelines[j - 1] = filelines[j];
                }
                
                File.WriteAllLines("CS6326Asg2.txt", newfilelines);

                int topItemIndex = 0;
                topItemIndex = listView1.TopItem.Index;
                this.LoadFile();
                if (listView1.Items.Count != 0)
                {
                    listView1.TopItem = listView1.Items[topItemIndex]; //scroll to the last position
                }
            }

            

            
        }

        private void button5_Click(object sender, EventArgs e)  //modify btn
        {
            if (listView1.SelectedItems.Count != 1)
                MessageBox.Show("Please select one line from the table to modify.  :)");
            if (listView1.SelectedItems.Count == 1)
            {               
                string[] filelines = File.ReadAllLines("CS6326Asg2.txt");
                string[] newfilelines = new string[filelines.Length];
                ListViewItem item = listView1.SelectedItems[0];
                string search = item.SubItems[1].Text + "\t" + item.SubItems[2].Text + "\t" + item.SubItems[3].Text;
                string newtext = null;
                string[] contents = new string[15];
                contents[0] = textBox1.Text;
                contents[1] = textBox2.Text;
                contents[2] = textBox3.Text;
                contents[3] = textBox4.Text;
                contents[4] = textBox5.Text;
                contents[5] = textBox6.Text;
                contents[6] = textBox7.Text;
                contents[7] = textBox8.Text;
                contents[8] = textBox9.Text;
                contents[9] = textBox10.Text;
                contents[10] = null;
                contents[11] = textBox11.Text;
                contents[12] = item.SubItems[13].Text;
                contents[13] = item.SubItems[14].Text;
                contents[14] = item.SubItems[15].Text;                            
                if (radioButton1.Checked && !radioButton2.Checked)
                    contents[10] = "YES";
                if (!radioButton1.Checked && radioButton2.Checked)
                    contents[10] = "NO";

                //email validation
                string strRegex = @"^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}" + @"\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\" + @".)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$";
                Regex re = new Regex(strRegex);        

                //date validation
                DateTime dr;

                if (string.IsNullOrWhiteSpace(contents[0]) || string.IsNullOrWhiteSpace(contents[1]) == true)
                    MessageBox.Show("Please complete the name.  :)");
                else if (string.IsNullOrWhiteSpace(contents[8]) == true)
                    MessageBox.Show("Please enter a phone number.  :)");
                else if (this.checkFullname())
                    MessageBox.Show("Please enter a different name.  :)");
                else if (!re.IsMatch(textBox10.Text) && !string.IsNullOrWhiteSpace(textBox10.Text) && (textBox10.Text!="unk."))
                    MessageBox.Show("Please enter a valid e-mail address.  :)");
                else if (!DateTime.TryParse(textBox11.Text, out dr))
                    MessageBox.Show("Please enter a valid date MM/DD/YYYY.  :)");
                else
                {
                    for (int m = 0; m < 15; m++)
                    {
                         if (string.IsNullOrWhiteSpace(contents[m]))
                              contents[m] = "unk."; //unkown
                         newtext = newtext + contents[m] + "\t";
                     }          

                    bool found = false;
                    int i;
                    for (i = 0; i < filelines.Length && !found; i++)
                    {
                          if (filelines[i].Contains(search))
                              found = true;
                          newfilelines[i] = filelines[i];
                     }

                    if (!found)
                       return;

                    if (i == filelines.Length)
                        newfilelines[i-1]=newtext;

                    newfilelines[i - 1] = newtext;
                    int j;
                    for (j = i; j < filelines.Length; j++)
                    {
                        newfilelines[j] = filelines[j];
                    }

                    File.WriteAllLines("CS6326Asg2.txt", newfilelines);

                    int topItemIndex = 0;
                    topItemIndex = listView1.TopItem.Index;
                    this.LoadFile();
                    if (listView1.Items.Count != 0)
                    {
                        listView1.TopItem = listView1.Items[topItemIndex]; //scroll to the last position
                    }
                }
            }
        }

        private void textBox1_TextChanged(object sender, EventArgs e)
        {
            clickTime1 = DateTime.Now;
        }

        //some of the validity filters      
        private void textBox3_KeyPress(object sender, KeyPressEventArgs e)//middle initial - character
        {
            e.Handled = char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar);
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox7_KeyPress(object sender, KeyPressEventArgs e) //state - character
        {
            e.Handled = char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar);
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox8_KeyPress(object sender, KeyPressEventArgs e) //zip code - digits and hyphen
        {
            e.Handled = !char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != '-';
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox9_KeyPress(object sender, KeyPressEventArgs e) //phone number - digits and hyphen
        {
            e.Handled = !char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != '-';
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox11_KeyPress(object sender, KeyPressEventArgs e) //date - digits and slash
        {
            e.Handled = !char.IsDigit(e.KeyChar) && !char.IsControl(e.KeyChar) && e.KeyChar != '/';
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        
        private void listView1_ColumnWidthChanging(object sender, ColumnWidthChangingEventArgs e) //width of listview
        {
            e.Cancel = true;
            e.NewWidth = listView1.Columns[e.ColumnIndex].Width;
        }

        private void textBox1_KeyDown(object sender, KeyEventArgs e)
        {

        }

        private void textBox2_KeyDown(object sender, KeyEventArgs e)
        {

        }

        private void textBox4_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox1_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox2_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox5_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox6_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }

        private void textBox10_KeyPress(object sender, KeyPressEventArgs e)
        {
            if (e.KeyChar == (char)Keys.Back)
                backspace++;
        }
    }
}
